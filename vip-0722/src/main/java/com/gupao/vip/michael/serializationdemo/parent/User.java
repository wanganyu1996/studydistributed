package com.gupao.vip.michael.serializationdemo.parent;

import java.io.Serializable;

public class User extends SuperUser  implements Serializable{
    private static final long serialVersionUID = -1657660549689020214L;

    @Override
    public String toString() {
        return "User{}"+super.toString();
    }
}
