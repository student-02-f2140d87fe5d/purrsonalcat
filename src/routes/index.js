const router = require('express').Router();

router.use('/auth', require('./auth'));
router.use('/predict', require('./predict'));

module.exports = router;
