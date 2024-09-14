 ### v1 nginx.conf file excluded from Dockerfile ###
# Stage 1: Build
FROM node:20.16.0-slim AS node-builder
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build -- --output-path=./dist/out --configuration production

# Stage 2: Serve
FROM nginx:stable-alpine3.20-perl
COPY --from=node-builder /app/dist/out /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
