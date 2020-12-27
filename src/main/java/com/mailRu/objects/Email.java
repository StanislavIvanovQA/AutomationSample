package com.mailRu.objects;

public class Email {
    private String to;
    private String subject;
    private String body;

    private Email(Builder builder) {
        this.to = builder.to;
        this.subject = builder.subject;
        this.body = builder.body;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public static class Builder {
        private final String to;
        private final String subject;
        private String body = "";

        public Builder(String to, String subject) {
            this.to = to;
            this.subject = subject;
        }

        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        public Email build() {
            return new Email(this);
        }
    }
}
