/* eslint-disable max-classes-per-file */
class NOT_FOUND extends Error {
  constructor(message) {
    super(message);
    this.name = 'NOT_FOUND';
    this.code = 404;
    this.message = message;
  }
}

class BAD_REQUEST extends Error {
  constructor(message) {
    super(message);
    this.name = 'BAD_REQUEST';
    this.code = 400;
    this.message = message;
  }
}

class UNAUTHORIZED extends Error {
  constructor(message) {
    super(message);
    this.name = 'UNAUTHORIZED';
    this.code = 401;
    this.message = message;
  }
}

module.exports = {
  NOT_FOUND,
  BAD_REQUEST,
  UNAUTHORIZED,
};
