## Database Design
### Entity Relationship Diagram

```
User (AWS Cognito user data stored locally)
├── cognito_id (PK, VARCHAR)
├── email (VARCHAR)
├── username (VARCHAR)
├── created_at (TIMESTAMP)

Source (Recommendation source tags)
├── id (PK, INT AUTO_INCREMENT)
├── user_id (FK → User.cognito_id)
├── name (VARCHAR) - e.g., "Sarah", "NYT Podcast", "Instagram"
├── color (VARCHAR) - hex color for UI badges
├── created_at (TIMESTAMP)

Recommendation
├── id (PK, INT AUTO_INCREMENT)
├── user_id (FK → User.cognito_id)
├── source_id (FK → Source.id, nullable)
├── media_id (FK → Media.id, nullable for manual entries)
├── notes (TEXT)
├── is_watched (BOOLEAN, default false)
├── created_at (TIMESTAMP)
├── updated_at (TIMESTAMP)

Media (TMDB data cached locally)
├── id (PK, INT AUTO_INCREMENT)
├── tmdb_id (INT UNIQUE) - The ID from TMDB API
├── title (VARCHAR)
├── media_type (ENUM: 'movie', 'tv')
├── year (INT)
├── poster_path (VARCHAR) - relative path from TMDB
├── overview (TEXT)
├── genres (VARCHAR) - comma-separated or JSON
├── created_at (TIMESTAMP)
```

### Relationships (All One-to-Many)

- **User → Sources**: One-to-Many (a user has many sources)
- **User → Recommendations**: One-to-Many (a user has many recommendations)
- **Source → Recommendations**: One-to-Many (a source has many recommendations, but each recommendation has only one source)
- **Media → Recommendations**: One-to-Many (a media item can be recommended by multiple users, but each recommendation references one media item)

**Total: 4 one-to-many relationships**

