/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.fon.common.domain;

import java.io.Serializable;

/**
 * Enumeration representing a state that indicates status for database
 * operations. Each value signifies a specific action relevant to the domain
 * class possessing this enum attribute.
 *
 * <p>
 * All domain classes that have compositions and / or associative classes and
 * that support UPDATE MySQL operation should contain this enum.
 * </p>
 *
 * @author Aleksa
 * @since 0.0.1
 */
public enum State implements Serializable {
    /**
     * State denoting database will execute INSERT query for the domain class
     * possessing this enum attribute.
     */
    CREATED,
    /**
     * State denoting database won't execute any query for the domain class
     * possessing this enum attribute.
     */
    UNCHANGED,
    /**
     * State denoting database will execute UPDATE query for the domain class
     * possessing this enum attribute.
     */
    CHANGED,
    /**
     * State denoting database will execute DELETE query for the domain class
     * possessing this enum attribute.
     */
    DELETED;
}
