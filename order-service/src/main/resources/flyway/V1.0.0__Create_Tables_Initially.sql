CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS orders (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    status VARCHAR(10) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
    expired_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS ordered_items (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    product_id UUID NOT NULL,
    warehouse_ref VARCHAR NOT NULL,
    quantity INT NOT NULL,
    product_price NUMERIC(12, 2) NOT NULL CHECK (product_price >= 0),
    total_price NUMERIC(12, 2) NOT NULL CHECK (total_price >= 0),
    order_id UUID NOT NULL
);

ALTER TABLE ordered_items
ADD CONSTRAINT orders_has_ordered_items_fk FOREIGN KEY (order_id)
REFERENCES orders (id) ON DELETE RESTRICT ON UPDATE CASCADE;
