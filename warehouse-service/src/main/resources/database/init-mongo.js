db = db.getSiblingDB('warehouse');

db.createUser({
  user: 'mongo',
  pwd: 'mongo',
  roles: [
    { role: 'readWrite', db: 'warehouse' }
  ]
});

db.createCollection('availability');

db.availability.insertMany([
  {
    ref: "KIE-101-A",
    availableItemsCount: 10,
    reservedCount: 0
  },
  {
    ref: "LVI-202-B",
    availableItemsCount: 4,
    reservedCount: 0
  },
  {
    ref: "ODS-303-C",
    availableItemsCount: 15,
    reservedCount: 0
  },
  {
    ref: "DNK-404-D",
    availableItemsCount: 40,
    reservedCount: 0
  },
  {
    ref: "KIE-505-E",
    availableItemsCount: 17,
    reservedCount: 0
  },
  {
    ref: "LVI-606-F",
    availableItemsCount: 8,
    reservedCount: 0
  },
  {
    ref: "ODS-707-G",
    availableItemsCount: 0,
    reservedCount: 0
  },
  {
    ref: "DNK-808-H",
    availableItemsCount: 1,
    reservedCount: 0
  },
  {
    ref: "KIE-909-I",
    availableItemsCount: 17,
    reservedCount: 0
  },
  {
    ref: "LVI-010-J",
    availableItemsCount: 47,
    reservedCount: 0
  }
]);