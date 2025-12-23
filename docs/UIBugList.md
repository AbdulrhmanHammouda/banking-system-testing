# UI Bug List

Reference: term_project_fall_2026

---

## Summary

During UI simulation testing, the following issues were identified and documented.

---

## Bug List

### BUG-001: No Account Selected Error Not Displayed
**Status:** Fixed  
**Severity:** Medium  
**Description:** When attempting operations without selecting an account, error message was null instead of descriptive.  
**Fix:** Added null check and proper error message in ClientController.

### BUG-002: Button States Not Updated After Status Change
**Status:** Fixed  
**Severity:** High  
**Description:** After changing account status, button enabled/disabled states did not refresh automatically.  
**Fix:** Added `updateButtonStates()` call after status changes.

---

## No Outstanding Bugs

All identified UI issues have been resolved and verified through UISimulationTests.

---

## Test Evidence

| Test | Scenario | Expected | Actual | Status |
|------|----------|----------|--------|--------|
| UI01 | No account - all disabled | All false | All false | ✓ Pass |
| UI02 | Verified - all enabled | All true | All true | ✓ Pass |
| UI03 | Closed - all disabled | All false | All false | ✓ Pass |
| UI04 | Success shows status | Not null | Not null | ✓ Pass |
| UI05 | Error shows message | Contains error | Contains error | ✓ Pass |
