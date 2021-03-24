-- Table: empire.player

-- DROP TABLE empire.player;

CREATE TABLE empire.player
(
    id integer NOT NULL DEFAULT nextval('empire.player_id_seq'::regclass),
    name character varying(40) COLLATE pg_catalog."default" NOT NULL,
    color integer NOT NULL,
    CONSTRAINT player_id PRIMARY KEY (id),
    CONSTRAINT color_not_negative CHECK (color >= 0),
    CONSTRAINT color_3_byte CHECK (color <= 16777215)
)

TABLESPACE pg_default;

ALTER TABLE empire.player
    OWNER to postgres;

COMMENT ON TABLE empire.player
    IS 'Player attributes.';

COMMENT ON COLUMN empire.player.name
    IS 'Limit to 40 to avoid UI problems with very long names.';

COMMENT ON COLUMN empire.player.color
    IS 'Stored as a 3-byte triplet. The upper byte is not used.';