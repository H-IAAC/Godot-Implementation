package com.example.visualizermind.util;

import java.util.Vector;

public class Basis {
    public Vector3 v_x;
    public Vector3 v_y;
    public Vector3 v_z;

    public Basis() {
        v_x = new Vector3(1, 0, 0);
        v_y = new Vector3(0, 1, 0);
        v_z = new Vector3(0, 0, 1);
    }

    public Basis(float x, float y, float z, float w) {
        /*
            Creates a basis equivalent to the given quaternion
        */

        v_x = (new Vector3(1, 0, 0).rotateQuat(x, y, z, w));
        v_y = (new Vector3(0, 1, 0).rotateQuat(x, y, z, w));
        v_z = (new Vector3(0, 0, 1).rotateQuat(x, y, z, w));
    }

    public Basis(Vector3 v_x, Vector3 v_y, Vector3 v_z) {
        this.v_x = v_x;
        this.v_y = v_y;
        this.v_z = v_z;
    }

    public boolean equals(Basis b) {
        return (v_x.equals(b.v_x) && v_y.equals(b.v_y) && v_z.equals(b.v_z));
    }

    public Basis rotateQuat(float x, float y, float z, float w) {
        /*
            Returns this basis rotated by the given quaternion
        */

        Vector3 new_v_x = v_x.rotateQuat(x, y, z, w);
        Vector3 new_v_y = v_y.rotateQuat(x, y, z, w);
        Vector3 new_v_z = v_z.rotateQuat(x, y, z, w);

        return new Basis(new_v_x, new_v_y, new_v_z);
    }

    private Vector3 solve3VarLinearEquation(float w1, float w2, float w3, float c1, float c2, float c3, float c4, float c5, float c6, float c7, float c8, float c9) {
        /*
            Returns X given W and C

             W = CX
             w1     c1 c2 c3   x
             w2  =  c4 c5 c6   y
             w3     c7 c8 c9   z

             Returns (0, 0, 0) if the system doesn't have a unique solution
        */

        float D = c1*c5*c9 + c2*c6*c7 + c3*c4*c8 - c3*c5*c7 - c2*c4*c9 - c1*c6*c8;

        if (D == 0) {
            return new Vector3();
        }

        float x = (w1*c5*c9 + c2*c6*w3 + c3*w2*c8 - c3*c5*w3 - c2*w2*c9 - w1*c6*c8)/D;
        float y = (c1*w2*c9 + w1*c6*c7 + c3*c4*w3 - c3*w2*c7 - w1*c4*c9 - c1*c6*w3)/D;
        float z = (c1*c5*w3 + c2*w2*c7 + w1*c4*c8 - w1*c5*c7 - c2*c4*w3 - c1*w2*c8)/D;

        return new Vector3(x, y, z);
    }

    public Vector3 fromFrameToGlobal(Vector3 w) {
        /*
            Given that w is expressed in this basis, returns w expressed in this basis's frame of
            reference. Effectively,

            w_g = w_1 * v_x + w_2 * v_y + w_3 * v_z
        */

        return ((v_x.multiply(w.x)).add(v_y.multiply(w.y))).add(v_z.multiply(w.z));
    }

    public Vector3 vectorToFrameOfReference(Vector3 w) {
        /*
            Given that this basis is represented in w's frame of reference, returns w
            expressed in this basis's frame of reference. Effectively, we are solving the
            system

            w = c_1 v_x + c_2 v_y + c_3 v_z

            and returning [c_1, c_2, c_3]
        */

        float D = v_x.x * v_y.y * v_z.z + v_y.x * v_z.y * v_x.z + v_z.x * v_x.y * v_y.z - v_z.x * v_y.y * v_x.z - v_y.x * v_x.y * v_z.z - v_x.x * v_z.y * v_y.z;

        if (D == 0) {
            return new Vector3(0, 0, 0);
        }

        float c_1 = (w.x * v_y.y * v_z.z + v_y.x * v_z.y * w.z + v_z.x * w.y * v_y.z - v_z.x * v_y.y * w.z - v_y.x * w.y * v_z.z - w.x * v_z.y * v_y.z)/D;
        float c_2 = (v_x.x * w.y * v_z.z + w.x * v_z.y * v_x.z + v_z.x * v_x.y * w.z - v_z.x * w.y * v_x.z - w.x * v_x.y * v_z.z - v_x.x * v_z.y * w.z)/D;
        float c_3 = (v_x.x * v_y.y * w.z + v_y.x * w.y * v_x.z + w.x * v_x.y * v_y.z - w.x * v_y.y * v_x.z - v_y.x * v_x.y * w.z - v_x.x * w.y * v_y.z)/D;

        return new Vector3(c_1, c_2, c_3);
    }

    public Basis orthonormalized() {
        /*
            Returns an orthonormalized basis produced thorugh the Gram-Schmidt Process
        */

        Vector3 new_v_x = v_x.copy();
        Vector3 new_v_y = v_y.projectToPlane(new_v_x);
        Vector3 new_v_z = (v_z.projectToPlane(v_x)).projectToPlane(new_v_y);

        new_v_x = new_v_x.normalized();
        new_v_y = new_v_y.normalized();
        new_v_z = new_v_z.normalized();

        return new Basis(new_v_x, new_v_y, new_v_z);
    }

    public Basis inverted() {
        /*
            Assuming this is the canonical basis represented in a specific basis, returns the
            specific basis represented in the canonical basis
        */

        Vector3 sol_x = solve3VarLinearEquation(1, 0, 0, v_x.x, v_x.y, v_x.z, v_y.x, v_y.y, v_y.z, v_z.x, v_z.y, v_z.z);
        Vector3 sol_y = solve3VarLinearEquation(0, 1, 0, v_x.x, v_x.y, v_x.z, v_y.x, v_y.y, v_y.z, v_z.x, v_z.y, v_z.z);
        Vector3 sol_z = solve3VarLinearEquation(0, 0, 1, v_x.x, v_x.y, v_x.z, v_y.x, v_y.y, v_y.z, v_z.x, v_z.y, v_z.z);

        Vector3 new_v_x = new Vector3(sol_x.x, sol_y.x, sol_z.x);
        Vector3 new_v_y = new Vector3(sol_x.y, sol_y.y, sol_z.y);
        Vector3 new_v_z = new Vector3(sol_x.z, sol_y.z, sol_z.z);

        return new Basis(new_v_x, new_v_y, new_v_z);
    }

    @Override
    public String toString() {
        return "[" + v_x + ", " + v_y + ", " + v_z + "]";
    }
}
