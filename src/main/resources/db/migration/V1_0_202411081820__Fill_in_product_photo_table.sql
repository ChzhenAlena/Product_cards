INSERT INTO product_photo (product_id, PHOTO_NAME)
VALUES
    ((SELECT id FROM product WHERE name = 'Apple iPhone 14 Pro'), 'Apple_iPhone_14_Pro_213841.png'),
    ((SELECT id FROM product WHERE name = 'Apple iPhone 14 Pro'), 'Apple_iPhone_14_Pro_971988.png'),
    ((SELECT id FROM product WHERE name = 'Samsung Galaxy S22 Ultra'), 'Samsung_Galaxy_S22_Ultra_804971.png'),
    ((SELECT id FROM product WHERE name = 'Samsung Galaxy S22 Ultra'), 'Samsung_Galaxy_S22_Ultra_945670.png'),
    ((SELECT id FROM product WHERE name = 'Sony X950H 65-inch 4K HDR LED TV'), 'Sony_X950H_65-inch_4K_HDR_LED_TV_701851.png'),
    ((SELECT id FROM product WHERE name = 'Bose QuietComfort 45 Wireless Headphones'), 'Bose_QuietComfort_45_Wireless_Headphones_643948.png'),
    ((SELECT id FROM product WHERE name = 'Bose QuietComfort 45 Wireless Headphones'), 'Bose_QuietComfort_45_Wireless_Headphones_892672.png'),
    ((SELECT id FROM product WHERE name = 'Dell XPS 17 9710 Laptop'), 'Dell_XPS_17_9710_Laptop_557561.png'),
    ((SELECT id FROM product WHERE name = 'Dyson V15 Detect Cordless Vacuum Cleaner'), 'Dyson_V15_Detect_Cordless_Vacuum_Cleaner_18998.png'),
    ((SELECT id FROM product WHERE name = 'Amazon Echo Show 10 (3rd Gen)'), 'Amazon_Echo_Show_10_(3rd_Gen)_141035.png'),
    ((SELECT id FROM product WHERE name = 'Amazon Echo Show 10 (3rd Gen)'), 'Amazon_Echo_Show_10_(3rd_Gen)_809780.png'),
    ((SELECT id FROM product WHERE name = 'GoPro HERO11 Black'), 'GoPro_HERO11_Black_848632.png'),
    ((SELECT id FROM product WHERE name = 'Logitech MX Master 3S Wireless Mouse'), 'Logitech_MX_Master_3S_Wireless_Mouse_97222.png'),
    ((SELECT id FROM product WHERE name = 'Sony PlayStation 5 Console'), 'Sony_PlayStation_5_Console_438716.png'),
    ((SELECT id FROM product WHERE name = 'Sony PlayStation 5 Console'), 'Sony_PlayStation_5_Console_998455.png');;