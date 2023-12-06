const Joi = require('joi');
const { User } = require('../models');

const loginSchema = Joi.object({
  email: Joi.string().email().required(),
  password: Joi.string().required(),
});

const registerSchema = Joi.object({
  username: Joi.string().min(3).max(30).required(),
  email: Joi.string()
    .email()
    .required()
    .external(async (value, helper) => {
      const user = await User.count({ where: { email: value } });
      if (user) {
        return helper.message('Email already exist', { email: value });
      }

      return value;
    }),
  password: Joi.string().required(),
});

module.exports = { loginSchema, registerSchema };
