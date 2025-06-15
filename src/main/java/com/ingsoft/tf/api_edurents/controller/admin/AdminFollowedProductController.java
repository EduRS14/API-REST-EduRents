package com.ingsoft.tf.api_edurents.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasRole('ADMIN')")
public class AdminFollowedProductController {
}
