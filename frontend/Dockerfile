# Stage 1: Build
FROM node:20.16.0-alpine AS node-builder
WORKDIR /app
COPY package*.json ./
RUN npm ci --only=production
COPY . .
RUN npm run build -- --output-path=./dist/out --configuration production
RUN rm -rf node_modules package-lock.json

# Stage 2: Runtime
FROM node:20.16.0-alpine AS runtime
WORKDIR /app
COPY --from=node-builder /app/dist/out /app
CMD ["npx", "http-server", "-p", "4200"]
