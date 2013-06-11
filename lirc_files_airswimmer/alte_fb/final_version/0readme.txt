Alle Files sind mittlerweile gerundet.
Bei no constant length werden zwischen zwei Befehlen standardmäßig 25ms eingefügt.
In der standardversion wird von Anfang des ersten Befehls bis Anfang des zweiten Befehls 115ms gewartet (ohne Berücksichtigung der Länge des Befehls)

Bei pre_data werden vor jedem Befehl die bei allen Befehlen gleichen Bytes 0xC9B1 gesendet.

Bei no constant length & pre_data sind beide Modifikationen drinnen.