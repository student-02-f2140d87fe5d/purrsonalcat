const database = require('../app/database');
const User = require('./user');

(async () => {
  try {
    database
      .sync({ force: false, alter: true })
      .then(() => console.log('Database synced'));
  } catch (error) {
    console.log('Unable to sync user model:', error);
  }
})();

module.exports = { User };
