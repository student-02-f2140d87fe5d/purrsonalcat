const { ValidationError } = require('joi');
const { BAD_REQUEST } = require('../errors');

const validation = (schema) => async (req, res, next) => {
  try {
    const { body, user } = req;

    let context = {};
    if (user) context = user;

    await schema.validateAsync(body, {
      abortEarly: false,
      context,
    });

    next();
  } catch (error) {
    if (error instanceof ValidationError) {
      const { details } = error;
      const message = details.map((i) => i.message).join(',');
      next(new BAD_REQUEST(message));
    }

    next(error);
  }
};

module.exports = validation;
