package com.main.APISchemas;

public class NotificationResponseSchema {
     private String subject;
     private String channel;
     private String body;

        public NotificationResponseSchema(String subject, String channel, String body) {
            this.subject = subject;
            this.channel = channel;
            this.body = body;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getSubject() {
            return subject;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getBody() {
            return body;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getChannel() {
            return channel;
        }


}
