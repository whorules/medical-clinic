CREATE TABLE event
(
    id                      uuid                    NOT NULL,
    appointment_id          uuid                    NOT NULL,
    status                  text                    NOT NULL,
    date                    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    status_changed_at       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    cancelled_by_id         uuid,
    cancellation_reason     text
);

ALTER TABLE event ADD CONSTRAINT event_pkey PRIMARY KEY (id);
ALTER TABLE event ADD CONSTRAINT fk_appointment FOREIGN KEY(appointment_id) REFERENCES appointment(id);
ALTER TABLE event ADD CONSTRAINT fk_cancelled_by FOREIGN KEY(cancelled_by_id) REFERENCES registered_user(id);