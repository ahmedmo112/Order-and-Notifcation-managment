package com.main.APISchemas;

public class NotFoundSchema extends MessageGenerator{

        public NotFoundSchema( String text) {
            super();
            message = text + " not found";
        }
}
