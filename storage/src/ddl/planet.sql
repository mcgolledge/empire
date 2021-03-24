-- Table: empire.planet

DROP TABLE empire.planet;

CREATE TABLE empire.planet
(
    id serial not null,
    player integer NOT NULL,
    production integer NOT NULL,
    x smallint NOT NULL,
    y smallint NOT NULL,
    is_known boolean,
	name text COLLATE pg_catalog."default" NOT NULL
)

TABLESPACE pg_default;

ALTER TABLE empire.planet
    OWNER to golledge;

COMMENT ON TABLE empire.planet
    IS 'Information specific to planets is stored in this structure.';

COMMENT ON COLUMN empire.planet.player
    IS 'ID of the player who owns the planet.';

COMMENT ON COLUMN empire.planet.x
    IS 'Offset from left of grid.';

COMMENT ON COLUMN empire.planet.y
    IS 'Offset from top of grid.';

COMMENT ON COLUMN empire.planet.is_known
    IS 'Flag for if the planet has been discovered by a player character.';