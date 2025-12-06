# JM Eureka Server - User Manual

## Overview

Service Discovery server. All microservices register here so the API Gateway can find them.

---

## Environment Variables

| Variable | Description | Example |
|----------|-------------|---------|
| `EUREKA_USERNAME` | Basic auth username | `eureka` |
| `EUREKA_PASSWORD` | Basic auth password | `your-secure-password` |
| `EUREKA_HOSTNAME` | Server hostname | `jm-eureka.onrender.com` |

---

## Deploy to Render

1. Push `eureka-server` folder to GitHub
2. Create **Web Service** on Render
3. Set environment variables:

| Key | Value |
|-----|-------|
| `EUREKA_USERNAME` | `eureka` |
| `EUREKA_PASSWORD` | `your-secure-password` |
| `EUREKA_HOSTNAME` | `jm-eureka-server.onrender.com` |
| `EUREKA_SELF_PRESERVATION` | `true` |

---

## For Other Services to Connect

Set this environment variable in your service:

```bash
EUREKA_URL=https://eureka:password@jm-eureka-server.onrender.com/eureka/
```

---

## Health Check

```bash
curl https://jm-eureka-server.onrender.com/actuator/health
```

---

## Dashboard

Visit: `https://jm-eureka-server.onrender.com`

Login with `EUREKA_USERNAME` and `EUREKA_PASSWORD`.
