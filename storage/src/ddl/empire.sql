--
-- PostgreSQL database dump
--

-- Dumped from database version 13.2 (Ubuntu 13.2-1.pgdg20.04+1)
-- Dumped by pg_dump version 13.2 (Ubuntu 13.2-1.pgdg20.04+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

ALTER TABLE ONLY game.planet DROP CONSTRAINT planet_owner_fkey;
ALTER TABLE ONLY game.fleet DROP CONSTRAINT fleet_owner_fkey;
DROP INDEX config.player_id_idx;
ALTER TABLE ONLY game.fleet DROP CONSTRAINT fleet_primary;
ALTER TABLE ONLY config.player DROP CONSTRAINT player_id_key;
ALTER TABLE ONLY config.player DROP CONSTRAINT player_id;
ALTER TABLE game.planet ALTER COLUMN id DROP DEFAULT;
ALTER TABLE config.player ALTER COLUMN id DROP DEFAULT;
DROP SEQUENCE game.planet_id_seq;
DROP TABLE game.planet;
DROP TABLE game.fleet;
DROP SEQUENCE game.fleet_id_seq;
DROP SEQUENCE config.player_id_seq;
DROP TABLE config.player;
DROP SCHEMA game;
DROP SCHEMA config;
--
-- Name: config; Type: SCHEMA; Schema: -; Owner: golledge
--

CREATE SCHEMA config;


ALTER SCHEMA config OWNER TO golledge;

--
-- Name: SCHEMA config; Type: COMMENT; Schema: -; Owner: golledge
--

COMMENT ON SCHEMA config IS 'Schema for configuration profiles of Empire';


--
-- Name: game; Type: SCHEMA; Schema: -; Owner: golledge
--

CREATE SCHEMA game;


ALTER SCHEMA game OWNER TO golledge;

--
-- Name: SCHEMA game; Type: COMMENT; Schema: -; Owner: golledge
--

COMMENT ON SCHEMA game IS 'Current state of the game objects';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: player; Type: TABLE; Schema: config; Owner: golledge
--

CREATE TABLE config.player (
    id integer NOT NULL,
    name character varying(40) NOT NULL,
    color integer NOT NULL,
    CONSTRAINT color_3_byte CHECK ((color <= 16777215)),
    CONSTRAINT color_not_negative CHECK ((color >= 0))
);


ALTER TABLE config.player OWNER TO golledge;

--
-- Name: TABLE player; Type: COMMENT; Schema: config; Owner: golledge
--

COMMENT ON TABLE config.player IS 'Player attributes.';


--
-- Name: COLUMN player.name; Type: COMMENT; Schema: config; Owner: golledge
--

COMMENT ON COLUMN config.player.name IS 'Limit to 40 to avoid UI problems with very long names.';


--
-- Name: COLUMN player.color; Type: COMMENT; Schema: config; Owner: golledge
--

COMMENT ON COLUMN config.player.color IS 'Stored as a 3-byte triplet. The upper byte is not used.';


--
-- Name: player_id_seq; Type: SEQUENCE; Schema: config; Owner: golledge
--

CREATE SEQUENCE config.player_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE config.player_id_seq OWNER TO golledge;

--
-- Name: player_id_seq; Type: SEQUENCE OWNED BY; Schema: config; Owner: golledge
--

ALTER SEQUENCE config.player_id_seq OWNED BY config.player.id;


--
-- Name: fleet_id_seq; Type: SEQUENCE; Schema: game; Owner: golledge
--

CREATE SEQUENCE game.fleet_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 2147483647
    CACHE 1;


ALTER TABLE game.fleet_id_seq OWNER TO golledge;

--
-- Name: fleet; Type: TABLE; Schema: game; Owner: golledge
--

CREATE TABLE game.fleet (
    id integer DEFAULT nextval('game.fleet_id_seq'::regclass) NOT NULL,
    owner integer,
    destination integer NOT NULL,
    num_ships integer NOT NULL,
    CONSTRAINT ships_positive CHECK ((num_ships > 0))
);


ALTER TABLE game.fleet OWNER TO golledge;

--
-- Name: TABLE fleet; Type: COMMENT; Schema: game; Owner: golledge
--

COMMENT ON TABLE game.fleet IS 'A fleet is a group of ships sent from one planet to another.';


--
-- Name: COLUMN fleet.owner; Type: COMMENT; Schema: game; Owner: golledge
--

COMMENT ON COLUMN game.fleet.owner IS 'System generated owner ID.';


--
-- Name: COLUMN fleet.destination; Type: COMMENT; Schema: game; Owner: golledge
--

COMMENT ON COLUMN game.fleet.destination IS 'ID of destination planet.';


--
-- Name: COLUMN fleet.num_ships; Type: COMMENT; Schema: game; Owner: golledge
--

COMMENT ON COLUMN game.fleet.num_ships IS 'Number of ships in the fleet; must be a positive integer.';


--
-- Name: CONSTRAINT ships_positive ON fleet; Type: COMMENT; Schema: game; Owner: golledge
--

COMMENT ON CONSTRAINT ships_positive ON game.fleet IS 'The number of ships must be a positive integer.';


--
-- Name: planet; Type: TABLE; Schema: game; Owner: golledge
--

CREATE TABLE game.planet (
    id integer NOT NULL,
    owner integer,
    production integer NOT NULL,
    x smallint NOT NULL,
    y smallint NOT NULL,
    is_known boolean,
    name text NOT NULL,
    CONSTRAINT production_positive CHECK ((production > 0))
);


ALTER TABLE game.planet OWNER TO golledge;

--
-- Name: TABLE planet; Type: COMMENT; Schema: game; Owner: golledge
--

COMMENT ON TABLE game.planet IS 'Information specific to planets is stored in this structure.';


--
-- Name: COLUMN planet.owner; Type: COMMENT; Schema: game; Owner: golledge
--

COMMENT ON COLUMN game.planet.owner IS 'ID of the player who owns the planet.';


--
-- Name: COLUMN planet.x; Type: COMMENT; Schema: game; Owner: golledge
--

COMMENT ON COLUMN game.planet.x IS 'Offset from left of grid.';


--
-- Name: COLUMN planet.y; Type: COMMENT; Schema: game; Owner: golledge
--

COMMENT ON COLUMN game.planet.y IS 'Offset from top of grid.';


--
-- Name: COLUMN planet.is_known; Type: COMMENT; Schema: game; Owner: golledge
--

COMMENT ON COLUMN game.planet.is_known IS 'Flag for if the planet has been discovered by a player character.';


--
-- Name: planet_id_seq; Type: SEQUENCE; Schema: game; Owner: golledge
--

CREATE SEQUENCE game.planet_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE game.planet_id_seq OWNER TO golledge;

--
-- Name: planet_id_seq; Type: SEQUENCE OWNED BY; Schema: game; Owner: golledge
--

ALTER SEQUENCE game.planet_id_seq OWNED BY game.planet.id;


--
-- Name: player id; Type: DEFAULT; Schema: config; Owner: golledge
--

ALTER TABLE ONLY config.player ALTER COLUMN id SET DEFAULT nextval('config.player_id_seq'::regclass);


--
-- Name: planet id; Type: DEFAULT; Schema: game; Owner: golledge
--

ALTER TABLE ONLY game.planet ALTER COLUMN id SET DEFAULT nextval('game.planet_id_seq'::regclass);


--
-- Data for Name: player; Type: TABLE DATA; Schema: config; Owner: golledge
--

COPY config.player (id, name, color) FROM stdin;
\.


--
-- Data for Name: fleet; Type: TABLE DATA; Schema: game; Owner: golledge
--

COPY game.fleet (id, owner, destination, num_ships) FROM stdin;
\.


--
-- Data for Name: planet; Type: TABLE DATA; Schema: game; Owner: golledge
--

COPY game.planet (id, owner, production, x, y, is_known, name) FROM stdin;
\.


--
-- Name: player_id_seq; Type: SEQUENCE SET; Schema: config; Owner: golledge
--

SELECT pg_catalog.setval('config.player_id_seq', 1, false);


--
-- Name: fleet_id_seq; Type: SEQUENCE SET; Schema: game; Owner: golledge
--

SELECT pg_catalog.setval('game.fleet_id_seq', 1, false);


--
-- Name: planet_id_seq; Type: SEQUENCE SET; Schema: game; Owner: golledge
--

SELECT pg_catalog.setval('game.planet_id_seq', 1, false);


--
-- Name: player player_id; Type: CONSTRAINT; Schema: config; Owner: golledge
--

ALTER TABLE ONLY config.player
    ADD CONSTRAINT player_id PRIMARY KEY (id);


--
-- Name: player player_id_key; Type: CONSTRAINT; Schema: config; Owner: golledge
--

ALTER TABLE ONLY config.player
    ADD CONSTRAINT player_id_key UNIQUE (id);


--
-- Name: fleet fleet_primary; Type: CONSTRAINT; Schema: game; Owner: golledge
--

ALTER TABLE ONLY game.fleet
    ADD CONSTRAINT fleet_primary PRIMARY KEY (id);


--
-- Name: player_id_idx; Type: INDEX; Schema: config; Owner: golledge
--

CREATE UNIQUE INDEX player_id_idx ON config.player USING btree (id);


--
-- Name: INDEX player_id_idx; Type: COMMENT; Schema: config; Owner: golledge
--

COMMENT ON INDEX config.player_id_idx IS 'Primary index on player table';


--
-- Name: fleet fleet_owner_fkey; Type: FK CONSTRAINT; Schema: game; Owner: golledge
--

ALTER TABLE ONLY game.fleet
    ADD CONSTRAINT fleet_owner_fkey FOREIGN KEY (owner) REFERENCES config.player(id);


--
-- Name: planet planet_owner_fkey; Type: FK CONSTRAINT; Schema: game; Owner: golledge
--

ALTER TABLE ONLY game.planet
    ADD CONSTRAINT planet_owner_fkey FOREIGN KEY (owner) REFERENCES config.player(id);


--
-- PostgreSQL database dump complete
--

