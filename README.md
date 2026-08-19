# AI Fitness Coach

A full-stack fitness and nutrition platform that generates personalized 30-day workout plans and 7-day meal plans using machine learning, with an admin console for managing the underlying content and a mobile app for end users.

This was a team project. **My contributions are the ML model server, the backend API, and the admin panel** — documented below. The Android mobile app was built by a teammate.

## Architecture

```
┌─────────────────┐      ┌──────────────────────┐      ┌─────────────────────┐
│   Android App    │─────▶│   Backend API         │─────▶│   MongoDB            │
│  (teammate-built) │      │  (Node.js/TypeScript,  │      │                       │
└─────────────────┘      │   Express, JWT auth)  │      └─────────────────────┘
                          │                        │
┌─────────────────┐      │   /api/v1/... (users)  │      ┌─────────────────────┐
│   Admin Panel     │─────▶│   /api/v1/... (console)│─────▶│  ML Model Server      │
│  (Next.js, MUI)   │      └──────────────────────┘      │  (Flask, deployed on  │
└─────────────────┘                                       │  Hugging Face Spaces) │
                                                            └─────────────────────┘
```

## 1. ML Model Server — `Python`, `Flask`, `scikit-learn`

Deployed as a Docker container on Hugging Face Spaces. Two endpoints:

**`POST /fitness`** — generates a personalized 30-day workout plan.
- A **K-Means model** clusters exercises into 90 clusters based on one-hot-encoded `level`, `goal`, and `bodyPart` features, so exercises with similar training characteristics group together.
- A separate **plan classifier** predicts which body parts to target on which day, based on the user's level, goal, and gender, producing a 30-day structure.
- For each day, the server picks a K-Means cluster matching the target body part/level/goal, filters out exercises requiring unavailable equipment (for home users), and randomly samples from the remaining pool — avoiding duplicate exercises within the plan.
- A **rule-based adjustment layer** on top of the ML output personalizes prescribed weight/duration and rep counts per exercise, using hand-tuned adjustment factors for gender, age bracket, training level, body part, and user feedback (whether the previous session felt manageable).

**`POST /nutrition`** — generates a 7-day meal plan (breakfast/lunch/dinner/snacks) from a target daily calorie count, using a trained nutrition model to match meals from a curated dataset (`meals.json`) against calorie and macro targets (fat, protein, carbs, sodium, fiber, sugar) split proportionally across meals of the day.

*Skills demonstrated: unsupervised clustering (K-Means) applied to a real recommendation problem, feature engineering (one-hot encoding), combining ML output with a rule-based adjustment layer to handle personalization the model alone couldn't, model serialization/deployment (pickle, Docker, Hugging Face Spaces).*

## 2. Backend API — `Node.js`, `TypeScript`, `Express`, `MongoDB`

A convention-driven REST API (`/api/v1/...`) with auto-discovered controllers, Joi request validation, JWT auth guards, Swagger docs, and an event-driven layer for tracking user activity.

**User-facing modules:** auth, users, home (daily goals & streaks), workouts, exercises, meal-plans, meals, ingredients, user-registered workouts (with progress tracking), user-registered meal plans (with progress tracking), templates, activities.

**Admin-facing modules** (behind a separate admin auth guard): admins, users, workouts, exercises, equipment, ingredients, meals, meal-plans, muscles — full CRUD for every entity the mobile app and ML server depend on.

Other things worth noting: an event system (`exercise-done`, `meal-done` events) decouples completing a workout/meal from the side effects of updating a user's progress and streaks; a seeder module for populating the database with fixture data during development; Cloudinary integration for media (exercise images/videos); Swagger-generated API documentation served at `/api/v1/docs`.

*Skills demonstrated: REST API design, JWT authentication, schema validation, MongoDB/Mongoose data modeling, event-driven architecture, API documentation.*

## 3. Admin Panel — `Next.js 14`, `React`, `MUI`, `Recharts`

An internal console for managing all the content the ML models and mobile app rely on. Every entity in the backend's console API gets a full CRUD interface: data-grid list views (`@mui/x-data-grid`), add/update forms, and a dashboard with charts (`@mui/x-charts`, `recharts`) for at-a-glance metrics.

**Managed entities:** Workouts, Exercises, Meal Plans, Meals, Ingredients, Equipment, Muscles, Users, Admins — plus a login flow and admin-account management (create/update admin).

*Skills demonstrated: building a data-heavy internal tool from scratch, integrating with a REST API + JWT auth, reusable CRUD component patterns (shared `DataViewTable`/`DataViewComponent` across 8+ entity types rather than duplicating per-entity UI), dashboard/chart design.*

## Mobile App (not my work)

The Android app (Kotlin/Java) was built by a teammate. It consumes the backend API and includes camera-based pose detection for exercise form tracking (ML Kit), workout/nutrition UI, and rep counting — noted here for completeness, not included in my contributions.

## Tech Stack
`Python` · `Flask` · `scikit-learn` · `Docker` · `Node.js` · `TypeScript` · `Express` · `MongoDB` · `Mongoose` · `JWT` · `Swagger` · `Next.js` · `React` · `Material UI` · `Recharts`

---

## What I'd highlight in an interview

- The **two-stage recommendation approach** for workouts: K-Means clustering handles "what kind of exercise fits this profile," while a separate classifier handles "what should today's session focus on" — splitting the problem this way made both models simpler than one model trying to do everything.
- The **rule-based layer on top of ML output** — pure model predictions needed guardrails (equipment availability, avoiding duplicate exercises, adjusting prescribed weight based on age/gender/feedback) that were more sensibly hand-coded than learned, a common real-world pattern.
- Designing the **admin panel's CRUD components to be reusable across 8+ different entity types** instead of building bespoke UI per entity — a maintainability decision that paid off as the number of manageable entities grew.
