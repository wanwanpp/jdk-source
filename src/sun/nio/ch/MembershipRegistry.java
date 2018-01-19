/*
 * Copyright (c) 2008, 2009, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package sun.nio.ch;

import sun.nio.ch.*;

import java.nio.channels.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.*;

/**
 * Simple registry of membership keys for a MulticastChannel.
 *
 * Instances of this object are not safe by multiple concurrent threads.
 */

class MembershipRegistry {

    // map multicast group to keys
    private Map<InetAddress,List<sun.nio.ch.MembershipKeyImpl>> groups = null;

    MembershipRegistry() {
    }

    /**
     * Checks registry for membership of the group on the given
     * network interface.
     */
    MembershipKey checkMembership(InetAddress group, NetworkInterface interf,
                                  InetAddress source)
    {
        if (groups != null) {
            List<sun.nio.ch.MembershipKeyImpl> keys = groups.get(group);
            if (keys != null) {
                for (sun.nio.ch.MembershipKeyImpl key: keys) {
                    if (key.networkInterface().equals(interf)) {
                        // already a member to receive all packets so return
                        // existing key or detect conflict
                        if (source == null) {
                            if (key.sourceAddress() == null)
                                return key;
                            throw new IllegalStateException("Already a member to receive all packets");
                        }

                        // already have source-specific membership so return key
                        // or detect conflict
                        if (key.sourceAddress() == null)
                            throw new IllegalStateException("Already have source-specific membership");
                        if (source.equals(key.sourceAddress()))
                            return key;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Add membership to the registry, returning a new membership key.
     */
    void add(sun.nio.ch.MembershipKeyImpl key) {
        InetAddress group = key.group();
        List<sun.nio.ch.MembershipKeyImpl> keys;
        if (groups == null) {
            groups = new HashMap<InetAddress,List<sun.nio.ch.MembershipKeyImpl>>();
            keys = null;
        } else {
            keys = groups.get(group);
        }
        if (keys == null) {
            keys = new LinkedList<sun.nio.ch.MembershipKeyImpl>();
            groups.put(group, keys);
        }
        keys.add(key);
    }

    /**
     * Remove a key from the registry
     */
    void remove(sun.nio.ch.MembershipKeyImpl key) {
        InetAddress group = key.group();
        List<sun.nio.ch.MembershipKeyImpl> keys = groups.get(group);
        if (keys != null) {
            Iterator<sun.nio.ch.MembershipKeyImpl> i = keys.iterator();
            while (i.hasNext()) {
                if (i.next() == key) {
                    i.remove();
                    break;
                }
            }
            if (keys.isEmpty()) {
                groups.remove(group);
            }
        }
    }

    /**
     * Invalidate all keys in the registry
     */
    void invalidateAll() {
        if (groups != null) {
            for (InetAddress group: groups.keySet()) {
                for (sun.nio.ch.MembershipKeyImpl key: groups.get(group)) {
                    key.invalidate();
                }
            }
        }
    }
}
