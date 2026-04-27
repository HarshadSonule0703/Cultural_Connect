package com.cultureconnect.programgrant.service;

import com.cultureconnect.programgrant.entity.GrantApplication;

public interface GrantService {

	public void disburseGrant(GrantApplication app, Double amount);
}