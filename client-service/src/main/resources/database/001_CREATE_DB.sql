DO $$
BEGIN
    IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'clients') THEN
       CREATE DATABASE clients;
END IF;
END $$ LANGUAGE plpgsql;