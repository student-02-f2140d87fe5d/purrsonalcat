const { BAD_REQUEST, UNAUTHORIZED, NOT_FOUND } = require('.');

// eslint-disable-next-line no-unused-vars
const ErrorHandler = (err, req, res, next) => {
  const { code = 500, message = 'Something went wrong' } = err;

  if (
    err instanceof BAD_REQUEST
    || err instanceof UNAUTHORIZED
    || err instanceof NOT_FOUND
  ) {
    res.status(code).json({ error: true, message });
  } else {
    console.log(err);
    res.status(code).json({ error: true, message: 'Internal server error' });
  }
};

module.exports = ErrorHandler;
