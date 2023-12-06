FROM node:lts-alpine3.18
WORKDIR /app
COPY . .
RUN npm install --production

ENV DB_HOST=127.0.0.1
ENV DB_PORT=3306
ENV DB_NAME=nodejs
ENV DB_USER=root
ENV DB_PASSWORD=secret

EXPOSE 3030
CMD ["npm", "start"]