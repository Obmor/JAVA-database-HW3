-- liquibase formatted sql

-- changeset sql-hogwarts:1
CREATE INDEX st_name_idx ON student (name);
-- changeset sql-hogwarts:2
CREATE INDEX fc_color_name_idx ON faculty (name, color);