CREATE TABLE IF NOT EXISTS release_reservation_outbox (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    status VARCHAR NOT NULL,
    warehouse_ref VARCHAR NOT NULL,
    quantity INT NOT NULL,
    order_id UUID NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    finished_at TIMESTAMP WITH TIME ZONE,
    last_error_message TEXT,

    CONSTRAINT release_reservation_outbox_has_orders_fk FOREIGN KEY (order_id)
    REFERENCES orders(id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT
);

CREATE INDEX idx_outbox_status_created ON release_reservation_outbox (status, created_at);
CREATE INDEX idx_outbox_order_id ON release_reservation_outbox (order_id);
CREATE INDEX idx_outbox_status ON release_reservation_outbox (created_at) WHERE status IN ('TO_EXECUTE', 'FAILED');