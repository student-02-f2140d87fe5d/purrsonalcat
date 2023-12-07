const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const {
  jwt: { secret, expiresIn },
  bcrypt: { saltRounds },
} = require('../app/config');
const { BAD_REQUEST } = require('../errors');
const { User } = require('../models');

const { success } = require('../app/formatter');

const register = async (req, res, next) => {
  try {
    const { username, email, password } = req.body;
    const hashPassword = bcrypt.hashSync(password, parseInt(saltRounds, 10));

    const user = await User.create({ username, email, password: hashPassword });

    delete user.dataValues.password;

    res.status(201).json(success(user, 'Register success'));
  } catch (error) {
    next(error);
  }
};

const login = async (req, res, next) => {
  try {
    const { email, password } = req.body;

    const user = await User.scope('withPassword').findOne({ where: { email } });
    if (!user) throw new BAD_REQUEST('Email or password is wrong.');

    const validPassword = await bcrypt.compare(password, user.password);
    if (!validPassword) throw new BAD_REQUEST('Email or password is wrong.');

    const payload = { id: user.id, username: user.username, email: user.email };
    const token = jwt.sign(payload, secret, { expiresIn });

    payload.token = token;

    res.status(200).json(success(payload, 'Login success'));
  } catch (error) {
    next(error);
  }
};

const me = async (req, res, next) => {
  try {
    const { user } = req;
    res.status(200).json(success(user, 'Get user success'));
  } catch (error) {
    next(error);
  }
};

module.exports = { register, login, me };
