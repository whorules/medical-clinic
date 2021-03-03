set search_path = public;

CREATE TABLE user_session
(
    id                      uuid                            NOT NULL,
    user_id                 uuid                            NOT NULL,
    expires_at              timestamp without time zone     NOT NULL
);

ALTER TABLE user_session ADD CONSTRAINT user_session_pkey PRIMARY KEY (id);
ALTER TABLE user_session ADD CONSTRAINT user_id_fkey FOREIGN KEY(user_id) REFERENCES registered_user(id);

