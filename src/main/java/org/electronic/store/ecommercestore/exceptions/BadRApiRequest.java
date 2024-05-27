package org.electronic.store.ecommercestore.exceptions;

public class BadRApiRequest extends RuntimeException{
    public BadRApiRequest(String message) {
        super(message);
    }
    public BadRApiRequest(){
        super("Bad Request");
    }
}
