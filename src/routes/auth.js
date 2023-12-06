const router = require('express').Router();

const authMiddleware = require('../middleware/authMiddleware');
const validate = require('../middleware/validateRequest');

const authController = require('../controllers/auth');
const { loginSchema, registerSchema } = require('../request/auth');

router.post('/register', validate(registerSchema), authController.register);
router.post('/login', validate(loginSchema), authController.login);
router.get('/me', authMiddleware, authController.me);

module.exports = router;
