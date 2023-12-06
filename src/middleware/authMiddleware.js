const jwt = require('jsonwebtoken');
const { UNAUTHORIZED } = require('../errors');
const {
  jwt: { secret },
} = require('../app/config');

module.exports = async function validate(req, res, next) {
  try {
    const { authorization } = req.headers;

    if (!authorization) throw new UNAUTHORIZED('You need to login first.');
    const token = authorization.split(' ')[1];

    if (!token) throw new UNAUTHORIZED('You need to login first.');
    const decoded = jwt.decode(token, secret);

    if (!decoded) throw new UNAUTHORIZED('You need to login first.');

    req.user = decoded;
    next();
  } catch (error) {
    next(error);
  }
};
