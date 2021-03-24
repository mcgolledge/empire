-- Table: empire.fleet

-- DROP TABLE empire.fleet;

CREATE TABLE empire.fleet
(
    id integer NOT NULL DEFAULT nextval('empire.fleet_id_seq'::regclass),
    owner integer NOT NULL,
    destination integer NOT NULL,
    num_ships integer NOT NULL,
    CONSTRAINT fleet_primary PRIMARY KEY (id),
    CONSTRAINT ships_positive CHECK (num_ships > 0)
)

TABLESPACE pg_default;

ALTER TABLE empire.fleet
    OWNER to golledge;

COMMENT ON TABLE empire.fleet
    IS 'A fleet is a group of ships sent from one planet to another.';

COMMENT ON COLUMN empire.fleet.owner
    IS 'System generated owner ID.';

COMMENT ON COLUMN empire.fleet.destination
    IS 'ID of destination planet.';

COMMENT ON COLUMN empire.fleet.num_ships
    IS 'Number of ships in the fleet; must be a positive integer.';

COMMENT ON CONSTRAINT ships_positive ON empire.fleet
    IS 'The number of ships must be a positive integer.';