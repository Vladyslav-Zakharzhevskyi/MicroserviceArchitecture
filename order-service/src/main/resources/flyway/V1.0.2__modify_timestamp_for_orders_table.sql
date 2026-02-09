ALTER TABLE orders
    ALTER COLUMN created_at TYPE timestamptz(0) USING created_at,
    ALTER COLUMN expired_at TYPE timestamptz(0) USING expired_at;

ALTER TABLE orders
    ALTER COLUMN created_at SET DEFAULT now();