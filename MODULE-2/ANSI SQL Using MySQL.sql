-- Create and use schema
CREATE DATABASE IF NOT EXISTS my_schema;
USE my_schema;

-- ================================
-- 1. Create Tables
-- ================================

CREATE TABLE Users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    city VARCHAR(100) NOT NULL,
    registration_date DATE NOT NULL
);

CREATE TABLE Events (
    event_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    city VARCHAR(100) NOT NULL,
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL,
    status ENUM('upcoming', 'completed', 'cancelled'),
    organizer_id INT,
    FOREIGN KEY (organizer_id) REFERENCES Users(user_id)
);

CREATE TABLE Sessions (
    session_id INT PRIMARY KEY AUTO_INCREMENT,
    event_id INT,
    title VARCHAR(200) NOT NULL,
    speaker_name VARCHAR(100) NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    FOREIGN KEY (event_id) REFERENCES Events(event_id)
);

CREATE TABLE Registrations (
    registration_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    event_id INT,
    registration_date DATE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (event_id) REFERENCES Events(event_id)
);

CREATE TABLE Feedback (
    feedback_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    event_id INT,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    comments TEXT,
    feedback_date DATE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (event_id) REFERENCES Events(event_id)
);

CREATE TABLE Resources (
    resource_id INT PRIMARY KEY AUTO_INCREMENT,
    event_id INT,
    resource_type ENUM('pdf', 'image', 'link'),
    resource_url VARCHAR(255) NOT NULL,
    uploaded_at DATETIME NOT NULL,
    FOREIGN KEY (event_id) REFERENCES Events(event_id)
);

-- ================================
-- 2. Insert Sample Data
-- ================================

-- Users
INSERT INTO Users VALUES
(1, 'Alice Johnson', 'alice@example.com', 'New York', '2024-12-01'),
(2, 'Bob Smith', 'bob@example.com', 'Los Angeles', '2024-12-05'),
(3, 'Charlie Lee', 'charlie@example.com', 'Chicago', '2024-12-10'),
(4, 'Diana King', 'diana@example.com', 'New York', '2025-01-15'),
(5, 'Ethan Hunt', 'ethan@example.com', 'Los Angeles', '2025-02-01');

-- Events
INSERT INTO Events VALUES
(1, 'Tech Innovators Meetup', 'A meetup for tech enthusiasts.', 'New York', '2025-06-10 10:00:00', '2025-06-10 16:00:00', 'upcoming', 1),
(2, 'AI & ML Conference', 'Conference on AI and ML advancements.', 'Chicago', '2025-05-15 09:00:00', '2025-05-15 17:00:00', 'completed', 3),
(3, 'Frontend Development Bootcamp', 'Hands-on training on frontend tech.', 'Los Angeles', '2025-07-01 10:00:00', '2025-07-03 16:00:00', 'upcoming', 2);

-- Sessions
INSERT INTO Sessions VALUES
(1, 1, 'Opening Keynote', 'Dr. Tech', '2025-06-10 10:00:00', '2025-06-10 11:00:00'),
(2, 1, 'Future of Web Dev', 'Alice Johnson', '2025-06-10 11:15:00', '2025-06-10 12:30:00'),
(3, 2, 'AI in Healthcare', 'Charlie Lee', '2025-05-15 09:30:00', '2025-05-15 11:00:00'),
(4, 3, 'Intro to HTML5', 'Bob Smith', '2025-07-01 10:00:00', '2025-07-01 12:00:00');

-- Registrations
INSERT INTO Registrations VALUES
(1, 1, 1, '2025-05-01'),
(2, 2, 1, '2025-05-02'),
(3, 3, 2, '2025-04-30'),
(4, 4, 2, '2025-04-28'),
(5, 5, 3, '2025-06-15');

-- Feedback
INSERT INTO Feedback VALUES
(1, 3, 2, 4, 'Great insights!', '2025-05-16'),
(2, 4, 2, 5, 'Very informative.', '2025-05-16'),
(3, 2, 1, 3, 'Could be better.', '2025-06-11');

-- Resources
INSERT INTO Resources VALUES
(1, 1, 'pdf', 'https://portal.com/resources/tech_meetup_agenda.pdf', '2025-05-01 10:00:00'),
(2, 2, 'image', 'https://portal.com/resources/ai_poster.jpg', '2025-04-20 09:00:00'),
(3, 3, 'link', 'https://portal.com/resources/html5_docs', '2025-06-25 15:00:00');

-- ================================
-- 3. Exercise Queries with Comments
-- ================================



-- 1. User Upcoming Events
-- Show a list of all upcoming events a user is registered for in their city, sorted by date.
SELECT u.full_name, e.title, e.start_date
FROM Users u
JOIN Registrations r ON u.user_id = r.user_id
JOIN Events e ON r.event_id = e.event_id
WHERE e.status = 'upcoming' AND e.city = u.city
ORDER BY e.start_date;

-- 2. Top Rated Events
-- Identify events with the highest average rating, considering only those with at least 10 feedbacks.
SELECT e.title, AVG(f.rating) AS avg_rating
FROM Events e
JOIN Feedback f ON e.event_id = f.event_id
GROUP BY e.event_id
HAVING COUNT(f.feedback_id) >= 10
ORDER BY avg_rating DESC;

-- 3. Inactive Users
-- Retrieve users who have not registered for any events in the last 90 days.
SELECT * FROM Users
WHERE user_id NOT IN (
    SELECT user_id FROM Registrations
    WHERE registration_date >= CURDATE() - INTERVAL 90 DAY
);

-- 4. Peak Session Hours
-- Count how many sessions are scheduled between 10 AM to 12 PM for each event.
SELECT e.title, COUNT(s.session_id) AS session_count
FROM Events e
JOIN Sessions s ON e.event_id = s.event_id
WHERE TIME(s.start_time) BETWEEN '10:00:00' AND '12:00:00'
GROUP BY e.event_id;

-- 5. Most Active Cities
-- List the top 5 cities with the highest number of distinct user registrations.
SELECT city, COUNT(DISTINCT u.user_id) AS total_users
FROM Users u
JOIN Registrations r ON u.user_id = r.user_id
GROUP BY city
ORDER BY total_users DESC
LIMIT 5;

-- 6. Event Resource Summary
-- Show the number of resources (PDFs, images, links) uploaded for each event.
SELECT e.title, 
       SUM(resource_type = 'pdf') AS pdfs,
       SUM(resource_type = 'image') AS images,
       SUM(resource_type = 'link') AS links
FROM Events e
LEFT JOIN Resources r ON e.event_id = r.event_id
GROUP BY e.event_id;

-- 7. Low Feedback Alerts
-- List all users who gave feedback with a rating less than 3.
SELECT u.full_name, f.comments, e.title
FROM Feedback f
JOIN Users u ON f.user_id = u.user_id
JOIN Events e ON f.event_id = e.event_id
WHERE f.rating < 3;

-- 8. Sessions per Upcoming Event
-- Display all upcoming events with the count of sessions scheduled for them.
SELECT e.title, COUNT(s.session_id) AS session_count
FROM Events e
LEFT JOIN Sessions s ON e.event_id = s.event_id
WHERE e.status = 'upcoming'
GROUP BY e.event_id;

-- 9. Organizer Event Summary
-- Show number of events created by each organizer, broken down by status.
SELECT u.full_name AS organizer, 
       COUNT(e.event_id) AS total_events,
       SUM(e.status = 'upcoming') AS upcoming,
       SUM(e.status = 'completed') AS completed,
       SUM(e.status = 'cancelled') AS cancelled
FROM Users u
JOIN Events e ON u.user_id = e.organizer_id
GROUP BY u.user_id;

-- 10. Feedback Gap
-- Identify events that had registrations but received no feedback at all.
SELECT e.title
FROM Events e
JOIN Registrations r ON e.event_id = r.event_id
LEFT JOIN Feedback f ON r.event_id = f.event_id
GROUP BY e.event_id
HAVING COUNT(f.feedback_id) = 0;

-- 11. Daily New User Count
-- Find the number of users who registered each day in the last 7 days.
SELECT registration_date, COUNT(*) AS new_users
FROM Users
WHERE registration_date >= CURDATE() - INTERVAL 7 DAY
GROUP BY registration_date;

-- 12. Event with Maximum Sessions
-- List the event(s) with the highest number of sessions.
SELECT e.title
FROM Events e
JOIN Sessions s ON e.event_id = s.event_id
GROUP BY e.event_id
ORDER BY COUNT(s.session_id) DESC
LIMIT 1;

-- 13. Average Rating per City
-- Calculate the average feedback rating of events conducted in each city.
SELECT city, AVG(rating) AS avg_rating
FROM Events e
JOIN Feedback f ON e.event_id = f.event_id
GROUP BY city;

-- 14. Most Registered Events
-- List top 3 events based on the total number of user registrations.
SELECT e.title, COUNT(*) AS registrations
FROM Events e
JOIN Registrations r ON e.event_id = r.event_id
GROUP BY e.event_id
ORDER BY registrations DESC
LIMIT 3;

-- 15. Event Session Time Conflict
-- Identify overlapping sessions within the same event.
SELECT s1.event_id, s1.session_id, s2.session_id
FROM Sessions s1
JOIN Sessions s2 ON s1.event_id = s2.event_id AND s1.session_id < s2.session_id
WHERE s1.end_time > s2.start_time AND s1.start_time < s2.end_time;

-- 16. Unregistered Active Users
-- Find users who registered in last 30 days but haven’t registered for any events.
SELECT * FROM Users
WHERE registration_date >= CURDATE() - INTERVAL 30 DAY
AND user_id NOT IN (SELECT user_id FROM Registrations);

-- 17. Multi-Session Speakers
-- Identify speakers with more than one session across all events.
SELECT speaker_name, COUNT(*) AS total_sessions
FROM Sessions
GROUP BY speaker_name
HAVING total_sessions > 1;

-- 18. Resource Availability Check
-- List all events that do not have any resources uploaded.
SELECT title FROM Events
WHERE event_id NOT IN (SELECT DISTINCT event_id FROM Resources);

-- 19. Completed Events with Feedback Summary
-- For completed events, show total registrations and average feedback rating.
SELECT e.title, COUNT(r.registration_id) AS total_registrations, AVG(f.rating) AS avg_rating
FROM Events e
LEFT JOIN Registrations r ON e.event_id = r.event_id
LEFT JOIN Feedback f ON e.event_id = f.event_id
WHERE e.status = 'completed'
GROUP BY e.event_id;

-- 20. User Engagement Index
-- For each user, calculate events attended and feedbacks submitted.
SELECT u.full_name, 
       COUNT(DISTINCT r.event_id) AS events_attended,
       COUNT(DISTINCT f.feedback_id) AS feedbacks_given
FROM Users u
LEFT JOIN Registrations r ON u.user_id = r.user_id
LEFT JOIN Feedback f ON u.user_id = f.user_id
GROUP BY u.user_id;

-- 21. Top Feedback Providers
-- List top 5 users who have submitted the most feedback entries.
SELECT u.full_name, COUNT(*) AS feedback_count
FROM Feedback f
JOIN Users u ON f.user_id = u.user_id
GROUP BY u.user_id
ORDER BY feedback_count DESC
LIMIT 5;

-- 22. Duplicate Registrations Check
-- Detect if a user has been registered more than once for the same event.
SELECT user_id, event_id, COUNT(*) AS count
FROM Registrations
GROUP BY user_id, event_id
HAVING count > 1;

-- 23. Registration Trends
-- Show a month-wise registration count trend over the past 12 months.
SELECT DATE_FORMAT(registration_date, '%Y-%m') AS month, COUNT(*) AS registrations
FROM Registrations
WHERE registration_date >= CURDATE() - INTERVAL 12 MONTH
GROUP BY month;

-- 24. Average Session Duration per Event
-- Compute the average session duration (in minutes) for each event.
SELECT e.title, 
       ROUND(AVG(TIMESTAMPDIFF(MINUTE, s.start_time, s.end_time)), 2) AS avg_duration
FROM Events e
JOIN Sessions s ON e.event_id = s.event_id
GROUP BY e.event_id;

-- 25. Events Without Sessions
-- List all events that currently have no sessions scheduled.
SELECT title FROM Events
WHERE event_id NOT IN (SELECT DISTINCT event_id FROM Sessions);

