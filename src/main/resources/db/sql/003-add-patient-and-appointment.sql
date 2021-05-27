CREATE TABLE patient
(
    id                      uuid                    NOT NULL,
    first_name              text                    NOT NULL,
    last_name               text                    NOT NULL,
    date_of_birth           TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    diagnosis               text                    NOT NULL,
    social_security_number  integer                 NOT NULL,
    status                  text                    NOT NULL,
    doctor_id               uuid                    NOT NULL,
    registered_at           TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    discharged_at           TIMESTAMP WITHOUT TIME ZONE
);

ALTER TABLE patient ADD CONSTRAINT patient_pkey PRIMARY KEY (id);
ALTER TABLE patient ADD CONSTRAINT fk_doctor FOREIGN KEY(doctor_id) REFERENCES registered_user(id);

CREATE TABLE appointment
(
    id                      uuid                    NOT NULL,
    patient_id              uuid                    NOT NULL,
    type                    text                    NOT NULL,
    registered_at           TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    created_by              uuid                    NOT NULL
);

ALTER TABLE appointment ADD CONSTRAINT appointment_pkey PRIMARY KEY (id);
ALTER TABLE appointment ADD CONSTRAINT fk_patient FOREIGN KEY(patient_id) REFERENCES patient(id);
ALTER TABLE appointment ADD CONSTRAINT fk_doctor FOREIGN KEY(created_by) REFERENCES registered_user(id);
