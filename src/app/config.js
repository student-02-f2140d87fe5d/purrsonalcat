require('dotenv').config();

const config = {
  db: {
    host: process.env.DB_HOST || 'localhost',
    port: process.env.DB_PORT || 3306,
    name: process.env.DB_NAME || 'example',
    dialect: process.env.DB_DIALECT || 'mysql',
    user: process.env.DB_USER || 'root',
    password: process.env.DB_PASSWORD || 'secret',
  },

  app: {
    port: process.env.PORT || 3000,
  },

  jwt: {
    secret: process.env.JWT_SECRET || 'secret',
    expiresIn: process.env.JWT_EXPIRES_IN || '30d',
  },

  bcrypt: {
    salfRound: process.env.BCRYPT_SALT_ROUND || 13,
  },
};

module.exports = config;
