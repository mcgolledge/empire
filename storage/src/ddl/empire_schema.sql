-- SCHEMA: empire

-- DROP SCHEMA empire ;

CREATE SCHEMA empire
    AUTHORIZATION golledge;

COMMENT ON SCHEMA empire
    IS 'Schema for game of Empire';

GRANT ALL ON SCHEMA empire TO golledge;