/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boh.jee.ejb.hotelagent.remotelib;

/**
 *
 * @author brian
 */
public class HAException extends Exception {

    /**
     * Creates a new instance of <code>HAException</code> without detail
     * message.
     */
    public HAException() {
    }

    /**
     * Constructs an instance of <code>HAException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public HAException(String msg) {
        super(msg);
    }
}
