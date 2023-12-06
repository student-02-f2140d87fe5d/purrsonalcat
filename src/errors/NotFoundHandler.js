const { NOT_FOUND } = require('.');

module.exports = (req, res, next) => {
  next(new NOT_FOUND(`Route ${req.originalUrl} not found.`));
};
