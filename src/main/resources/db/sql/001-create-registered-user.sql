set search_path = public;

CREATE TABLE registered_user
(
    id                      uuid                    NOT NULL,
    first_name              text                    NOT NULL,
    last_name               text                    NOT NULL,
    speciality              text                    NOT NULL,
    email                   text                    NOT NULL,
    password                text                    NOT NULL,
    activation_token        text,
    is_activated            boolean                 DEFAULT FALSE,
    registered_at           TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now()
);

ALTER TABLE registered_user ADD CONSTRAINT registered_user_pkey PRIMARY KEY (id);

