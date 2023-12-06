require('dotenv').config();
const { Sequelize } = require('sequelize');
const { db } = require('./config');

const sequelize = new Sequelize(db.name, db.user, db.password, {
  host: db.host,
  port: db.port,
  dialect: db.dialect,
  logging: false,
  define: {
    timestamps: true,
  },
  pool: {
    max: 5,
    min: 0,
    idle: 10000,
  },
});

(async () => {
  try {
    await sequelize
      .authenticate()
      .then(() => console.log('Connection has been established successfully.'));
  } catch (error) {
    console.log('Unable to connect to the database:', error);
  }
})();

module.exports = sequelize;
