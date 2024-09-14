# Stage 1: Build
FROM node:20.16.0-alpine AS node-builder
WORKDIR /app
COPY package*.json ./
RUN npm ci --only=production
COPY . .
RUN npm run build -- --output-path=./dist/out --configuration production

# Stage 2: Serve
FROM nginx:stable-alpine
COPY --from=node-builder /app/dist/out /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf
RUN touch /var/run/nginx.pid && \
    chown -R nginx:nginx /var/run/nginx.pid && \
    chown -R nginx:nginx /var/cache/nginx && \
    chown -R nginx:nginx /usr/share/nginx/html
USER nginx
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
