package com.android.iviet.connection;

import java.net.URI;

public interface AccessPathGenerator {
	URI everything();

	URI newest();

	URI feature();
}
