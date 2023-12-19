const { success } = require('../app/formatter');
const { predictionUrl } = require('../app/config');

// eslint-disable-next-line no-unused-vars
const getQuestions = (req, res, next) => {
  const questions = [
    'Batuk',
    'Bau mulut',
    'Benjolan atau pembengkakan yang tidak hilang',
    'Bersin',
    'Borok di Lidah, Gusi, Bibir, atau Hidung',
    'Buang Air kecil berlebih',
    'Buang Air Besar Berlebih',
    'Bulu rontok',
    'Darah dalam urin',
    'Dehidrasi',
    'Demam',
    'Diare',
    'Gusi Bengkak',
    'Gusi Merah atau Keunguan',
    'Gusi Pucat',
    'Infeksi saluran kemih',
    'Infeksi Kulit',
    'Infeksi telinga',
    'Infertilitas',
    'Kejang-kejang',
    'Kesulitan buang air besar',
    'Kesulitan Bernafas',
    'Kemerahan',
    'Kulit bersisik atau menglupas',
    'Kulit Warna Kuning',
    'Konjugtivitas',
    'Kulit berkerak',
    'Kurang Nafsu Makan',
    'Lecet',
    'Lemes atau Lemah',
    'Luka yang tidak sembuh',
    'Mata Berair',
    'Muntah',
    'Minum air lebih',
    'Pembengkakan',
    'Pembengkakan Kelenjar Getah Bening',
    'Penurunan Berat Badan',
    'Perut Kembung atau Membucit',
    'Perut Sensitif',
    'Pupil mata mengecil',
    'Tremor atau Trauma',
    'Radang',
    'Sulit Berjalan',
    'Sering Menggaruk',
  ];

  res.json(success(questions));
};

const processQuestion = async (req, res, next) => {
  try {
    const { answer } = req.body;

    console.log(answer);
    const response = await fetch(predictionUrl, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(answer),
    });

    const { predictions } = await response.json();

    res.json(success({ predictions: predictions[0] }));
  } catch (error) {
    console.error(error);
    next(error);
  }
};

module.exports = { processQuestion, getQuestions };
