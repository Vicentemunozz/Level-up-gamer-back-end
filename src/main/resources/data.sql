-- Esto insertará datos en la tabla 'products' cada vez que inicies el backend
-- OJO: Los nombres de columnas (image_url) deben coincidir con las variables (imageUrl)
INSERT INTO products (name, price, description, image_url) VALUES 
('Catan', 29990, 'Un clásico juego de estrategia', '/img/catan.jpg'),
('Carcassonne', 24990, 'Un juego de colocación de fichas', '/img/carcassonne.jpg'),
('Controlador Inalámbrico Xbox', 59990, 'Experiencia de juego cómoda', '/img/xbox-controller.jpg'),
('Auriculares HyperX Cloud II', 79990, 'Sonido envolvente 7.1', '/img/hyperx-headset.jpg'),
('PlayStation 5', 549990, 'La consola de última generación', '/img/ps5.jpg'),
('PC Gamer ASUS ROG Strix', 1299990, 'Potente PC para juegos', '/img/pc-gamer.jpg');