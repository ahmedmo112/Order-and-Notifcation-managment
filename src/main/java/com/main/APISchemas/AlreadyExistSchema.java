package com.main.APISchemas;

public class AlreadyExistSchema extends MessageGenerator{

        public AlreadyExistSchema(String text) {
            super();
            message = text + " already exists";
        }


}