const router = require('express').Router();

const { getQuestions, processQuestion } = require('../controllers/predicts');

router.get('/', getQuestions);
router.post('/', processQuestion);

module.exports = router;
